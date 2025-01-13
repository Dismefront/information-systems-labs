import Cookies from 'js-cookie';
import toast from 'react-hot-toast';

const origFetch = window.fetch;

export const fetchWrapper = async (
    input: RequestInfo | URL,
    init?: RequestInit
): Promise<Response> => {
    try {
        const response = await origFetch(input, init);
        if (response.status >= 400 || response.status === 206) {
            const text = await response.text();
            if (text) {
                toast.error(text.toString());
            }
        }
        if (response.status === 403) {
            Cookies.set('JSESSIONID', '');
            toast.error('Logged out');
        }
        return response;
    } catch (ex: any) {
        toast.error((ex as Error).message);
        return Promise.reject('Could not fetch from resourse');
    }
};
