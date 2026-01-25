import './global.css';
import { NavBar } from './widgets/navbar/NavBar';
import { Documentation } from './components/documentation/Documentation';
import { Requirements } from './components/requirements/Requirements';

export const API_ENDPOINT = `${import.meta.env.VITE_HTTP_SCHEMA}${import.meta.env.VITE_HOST}`;

export const App: React.FC = () => {
    return (
        <>
            <NavBar />
            <main>
                <Documentation />
                <Requirements />
            </main>
        </>
    );
};
