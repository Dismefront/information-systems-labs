import { useEffect } from 'react';
import { useNavigate } from 'react-router-dom';
import { useAuth } from './useAuth';

export const AuthKeeper: React.FC<React.PropsWithChildren> = ({ children }) => {
    const auth = useAuth();
    const navigate = useNavigate();
    useEffect(() => {
        if (!auth) {
            navigate('/login');
            return;
        }
    }, [auth, navigate]);
    return auth ? children : null;
};
