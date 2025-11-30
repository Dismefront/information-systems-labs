import { API_ENDPOINT } from '@/App';
import { useUnit } from 'effector-react';
import { useEffect } from 'react';
import toast from 'react-hot-toast';
import { useNavigate } from 'react-router-dom';
import { $currentUser } from '../auth-controller/auth';

export const BecomeAdmin = () => {
    const user = useUnit($currentUser)!;
    const navigate = useNavigate();
    useEffect(() => {
        fetch(`${API_ENDPOINT}/requests/post`, {
            method: 'post',
            body: JSON.stringify({
                userId: user.id,
            }),
            credentials: 'include',
            headers: {
                'Content-Type': 'application/json',
            },
        })
            .then((res) => res.text())
            .then((data) => {
                toast.success(data);
                navigate('/');
            });
    }, []);
    return null;
};
