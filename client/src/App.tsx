import { Toaster } from 'react-hot-toast';
import './global.css';
import { fetchWrapper } from './shared/fetch-wrapper/store';
import { NavBar } from './widgets/navbar/NavBar';

export const API_ENDPOINT = `${import.meta.env.VITE_HTTP_SCHEMA}${import.meta.env.VITE_HOST}`;

window.fetch = fetchWrapper;

export const App: React.FC = () => {
    return (
        <>
            <Toaster position="top-right" />
            <NavBar />
        </>
    );
};
