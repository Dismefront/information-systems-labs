import { useEffect } from 'react';
import toast from 'react-hot-toast';
import { useNavigate } from 'react-router-dom';
import { getCurrentUserEv } from './auth';
import { useAuth } from './useAuth';

export const AuthKeeper: React.FC<React.PropsWithChildren> = ({ children }) => {
    const auth = useAuth();
    const navigate = useNavigate();
    useEffect(() => {
        if (!auth) {
            toast.error('You are not logged in');
            navigate('/login');
            return;
        }
        getCurrentUserEv();
    }, [auth, navigate]);
    return auth ? children : null;
};
