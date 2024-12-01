import { NavBar } from '@/widgets/navbar/NavBar';
import { Pagination } from '@/widgets/pagination/Pagination';
import { CreatePersonPopup } from '@/widgets/persons/CreatePersonsPopup';
import { PersonsTable } from '@/widgets/persons/PersonsTable';
import { UpdatePersonPopup } from '@/widgets/persons/UpdatePersonsPopup';
import { useUnit } from 'effector-react';
import { useEffect } from 'react';
import { useParams } from 'react-router-dom';
import { persons } from './store';

export const PersonsPage: React.FC = () => {
    const data = useUnit(persons.$data);
    const params: { page: string } = useParams() as any;
    useEffect(() => {
        const interval = setInterval(() => {
            persons.fetchFx({
                page: Number(params.page) - 1,
                size: 10,
            });
        }, 1000);
        return () => {
            clearInterval(interval);
        };
    }, [params.page]);
    return (
        <>
            <NavBar />
            {data === null ? (
                'Загрузка'
            ) : (
                <>
                    <CreatePersonPopup />
                    <UpdatePersonPopup />
                    <PersonsTable data={data} />
                    <Pagination
                        path="/products"
                        totalPages={data.totalPages}
                        currentPage={data.number + 1}
                    />
                </>
            )}
        </>
    );
};
