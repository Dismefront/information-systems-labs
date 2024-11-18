import { useUnit } from 'effector-react';
import { useEffect } from 'react';
import toast from 'react-hot-toast';
import { useNavigate } from 'react-router-dom';
import { $currentUser, getCurrentUserEv } from './auth';
import { useAuth } from './useAuth';

interface AuthKeeperProps extends React.PropsWithChildren {
    adminRoute?: boolean;
}

export const AuthKeeper: React.FC<AuthKeeperProps> = ({ children, adminRoute }) => {
    const auth = useAuth();
    const navigate = useNavigate();
    const currentUser = useUnit($currentUser);
    useEffect(() => {
        if (!auth) {
            toast.error('You are not logged in');
            navigate('/login');
            return;
        }
        if (adminRoute && !currentUser?.roles.includes('ROLE_ADMIN')) {
            navigate('/');
        }
        getCurrentUserEv();
    }, [auth, navigate]);
    return auth ? children : null;
};
