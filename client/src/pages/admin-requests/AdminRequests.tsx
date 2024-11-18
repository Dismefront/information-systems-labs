import { RequestsTable } from '@/widgets/admin-requests/RequestsTable';
import { NavBar } from '@/widgets/navbar/NavBar';
import { useUnit } from 'effector-react';
import { useEffect } from 'react';
import { adminRequests } from './store';

export const AdminRequests: React.FC = () => {
    const data = useUnit(adminRequests.$data);
    useEffect(() => {
        adminRequests.fetchFx();
    }, []);
    return (
        <>
            <NavBar />
            {data === null ? (
                'Загрузка'
            ) : (
                <>
                    {data.length === 0 && 'Пока нет заявок'}
                    <RequestsTable data={data} />
                </>
            )}
        </>
    );
};
