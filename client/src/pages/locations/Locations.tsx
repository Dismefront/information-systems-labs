import { CreateLocationPopup } from '@/widgets/locations/CreateLocation';
import { LocationsTable } from '@/widgets/locations/LocationsTable';
import { UpdateLocationPopup } from '@/widgets/locations/UpdateLocationPopup';
import { NavBar } from '@/widgets/navbar/NavBar';
import { Pagination } from '@/widgets/pagination/Pagination';
import { useUnit } from 'effector-react';
import React, { useEffect } from 'react';
import { useParams } from 'react-router-dom';
import { locations } from './store';

export const LocationsPage: React.FC = () => {
    const data = useUnit(locations.$data);
    const params: { page: string } = useParams() as any;

    useEffect(() => {
        const interval = setInterval(() => {
            locations.fetchFx({
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
                    <CreateLocationPopup />
                    <UpdateLocationPopup />
                    <LocationsTable data={data} />
                    <Pagination
                        path="/locations"
                        totalPages={data.totalPages}
                        currentPage={data.number + 1}
                    />
                </>
            )}
        </>
    );
};
