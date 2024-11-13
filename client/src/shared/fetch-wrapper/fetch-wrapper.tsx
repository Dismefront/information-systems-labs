import toast from 'react-hot-toast';

const origFetch = window.fetch;

export const fetchWrapper = async (
    input: RequestInfo | URL,
    init?: RequestInit
): Promise<Response> => {
    try {
        const response = await origFetch(input, init);
        if (response.status >= 400) {
            const text = await response.text();
            toast.error(text.toString());
        }
        return response;
    } catch (ex: any) {
        toast.error((ex as Error).message);
        return Promise.reject('Could not fetch from resourse');
    }
};
