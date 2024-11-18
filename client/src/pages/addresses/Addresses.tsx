import { AddressTable } from '@/widgets/addresses/AddressTable';
import { CreateAddressPopup } from '@/widgets/addresses/CreateAddresses';
import { UpdateAddressPopup } from '@/widgets/addresses/UpdateAddresses';
import { NavBar } from '@/widgets/navbar/NavBar';
import { Pagination } from '@/widgets/pagination/Pagination';
import { useUnit } from 'effector-react';
import React, { useEffect } from 'react';
import { useParams } from 'react-router-dom';
import { address } from './store';

export const AddressPage: React.FC = () => {
    const data = useUnit(address.$data);
    const params: { page: string } = useParams() as any;

    useEffect(() => {
        const page = Number(params.page) - 1;
        address.fetchFx({
            page: page,
            size: 10,
        });
    }, [params.page]);

    return (
        <>
            <NavBar />
            {data === null ? (
                'Loading...'
            ) : (
                <>
                    <CreateAddressPopup />
                    <UpdateAddressPopup />
                    <AddressTable data={data} />
                    <Pagination
                        path="/address"
                        totalPages={data.totalPages}
                        currentPage={data.number + 1}
                    />
                </>
            )}
        </>
    );
};