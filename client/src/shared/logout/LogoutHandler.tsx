import { useEffect } from 'react';
import { useNavigate } from 'react-router-dom';
import { logoutFx, resetCurrentUserEv } from '../auth-controller/auth';

export const LogoutHandler: React.FC = () => {
    const navigate = useNavigate();
    useEffect(() => {
        logoutFx().then(() => {
            resetCurrentUserEv();
            navigate('/login');
        });
    }, []);
    return undefined;
};
