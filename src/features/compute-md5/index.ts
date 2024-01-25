import SparkMD5 from 'spark-md5';

export default (file: File | Blob): Promise<string> => {
    return new Promise((resolve, reject) => {
        const blobSlice = File.prototype.slice;
        const chunkSize = 2097152; // Read in chunks of 2MB
        const chunks = Math.ceil(file.size / chunkSize);
        let currentChunk = 0;
        const spark = new SparkMD5.ArrayBuffer();
        const fileReader = new FileReader();

        fileReader.onload = function (e: any) {
            spark.append(e.target.result); // Append array buffer
            currentChunk++;

            if (currentChunk < chunks) {
                loadNext();
            } else {
                const md5 = spark.end(); // Compute hash
                resolve(md5);
            }
        };

        fileReader.onerror = function () {
            reject(new Error('MD5 calculation error'));
        };

        function loadNext() {
            const start = currentChunk * chunkSize;
            const end = (start + chunkSize) >= file.size ? file.size : start + chunkSize;
            fileReader.readAsArrayBuffer(blobSlice.call(file, start, end));
        }

        loadNext();
    });
};