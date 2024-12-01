import { CoordinatesTable } from '@/widgets/coordinates/CoordinatesTable';
import { CreateCoordinatesPopup } from '@/widgets/coordinates/CreateCoordinates';
import { UpdateCoordinatesPopup } from '@/widgets/coordinates/UpdateCoordinates';
import { NavBar } from '@/widgets/navbar/NavBar';
import { Pagination } from '@/widgets/pagination/Pagination';
import { useUnit } from 'effector-react';
import React, { useEffect } from 'react';
import { useParams } from 'react-router-dom';
import { coordinates } from './store';

export const CoordinatesPage: React.FC = () => {
    const data = useUnit(coordinates.$data);
    const params: { page: string } = useParams() as any;

    useEffect(() => {
        const interval = setInterval(() => {
            coordinates.fetchFx({
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
                'Loading...'
            ) : (
                <>
                    <CreateCoordinatesPopup />
                    <UpdateCoordinatesPopup />
                    <CoordinatesTable data={data} />
                    <Pagination
                        path="/coordinates"
                        totalPages={data.totalPages}
                        currentPage={data.number + 1}
                    />
                </>
            )}
        </>
    );
};
