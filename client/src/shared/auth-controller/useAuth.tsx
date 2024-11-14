import Cookies from 'js-cookie';
import { useEffect, useState } from 'react';

export const useAuth = () => {
    const [isAuthenticated, setAuthenticated] = useState<boolean>();
    useEffect(() => {
        const sessionCookie = Cookies.get('JSESSIONID');
        setAuthenticated(Boolean(sessionCookie));
    }, []);

    return isAuthenticated;
};
