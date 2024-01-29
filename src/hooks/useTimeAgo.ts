export default function useTimeAgo(previousTime: Date): string {
    const currentTime = new Date();
    const timeDifferenceInSeconds = Math.floor((currentTime.getTime() - previousTime.getTime()) / 1000);

    const minuteInSeconds = 60;
    const hourInSeconds = 3600;
    const dayInSeconds = 86400;
    const weekInSeconds = 604800;

    if (timeDifferenceInSeconds < minuteInSeconds) {
        return 'Less than a minute ago';
    } else if (timeDifferenceInSeconds < hourInSeconds) {
        const minutesAgo = Math.floor(timeDifferenceInSeconds / minuteInSeconds);
        return `${minutesAgo} minute${minutesAgo > 1 ? 's' : ''} ago`;
    } else if (timeDifferenceInSeconds < dayInSeconds) {
        const hoursAgo = Math.floor(timeDifferenceInSeconds / hourInSeconds);
        return `${hoursAgo} hour${hoursAgo > 1 ? 's' : ''} ago`;
    } else if (timeDifferenceInSeconds < weekInSeconds) {
        const daysAgo = Math.floor(timeDifferenceInSeconds / dayInSeconds);
        return `${daysAgo} day${daysAgo > 1 ? 's' : ''} ago`;
    } else {
        return previousTime.toLocaleDateString();
    }
};