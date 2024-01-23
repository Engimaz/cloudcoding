
import { useEffect } from 'react';
import { Terminal } from 'primereact/terminal';
import { TerminalService } from 'primereact/terminalservice';

export default function TerminalDemo() {
    const commandHandler = (text: string) => {
        let response: string;
        let argsIndex: number = text.indexOf(' ');
        let command: string = argsIndex !== -1 ? text.substring(0, argsIndex) : text;

        switch (command) {
            case 'date':
                response = 'Today is ' + new Date().toDateString();
                break;

            case 'greet':
                response = 'Hola ' + text.substring(argsIndex + 1) + '!';
                break;

            case 'random':
                response = Math.floor(Math.random() * 100).toString();
                break;

            case 'clear':
                response = "";
                break;

            default:
                response = 'Unknown command: ' + command;
                break;
        }

        if (response)
            TerminalService.emit('response', response);
        else
            TerminalService.emit('clear');
    };

    useEffect(() => {
        TerminalService.on('command', commandHandler);

        return () => {
            TerminalService.off('command', commandHandler);
        };
    }, []);

    return (
        <Terminal
            welcomeMessage="Welcome to PrimeReact"
            prompt="primereact $"
            pt={{
                root: { className: "h-full" }
            }}
        />
    );
}
