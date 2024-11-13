import { useEffect } from 'react';
import { useNavigate } from 'react-router-dom';

export const LogoutHandler: React.FC = () => {
    const navigate = useNavigate();
    useEffect(() => {
        console.log(document.cookie);
        navigate('/login');
    }, []);
    return undefined;
};
