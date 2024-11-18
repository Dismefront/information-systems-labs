import { NavBar } from '@/widgets/navbar/NavBar';
import { CreateOrganizationPopup } from '@/widgets/organizations/CreateOrganizationPopup';
import { OrganizationsTable } from '@/widgets/organizations/OrganizationTable';
import { UpdateOrganizationPopup } from '@/widgets/organizations/UpdateOrganizationPopup';
import { Pagination } from '@/widgets/pagination/Pagination';
import { useUnit } from 'effector-react';
import { useEffect } from 'react';
import { useParams } from 'react-router-dom';
import { organizations } from './store';

export const OrganizationsPage: React.FC = () => {
    const data = useUnit(organizations.$data);
    const params: { page: string } = useParams() as any;

    useEffect(() => {
        organizations.fetchFx({
            page: Number(params.page) - 1,
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
                    <CreateOrganizationPopup />
                    <UpdateOrganizationPopup />
                    <OrganizationsTable data={data} />
                    <Pagination
                        path="/organizations"
                        totalPages={data.totalPages}
                        currentPage={data.number + 1}
                    />
                </>
            )}
        </>
    );
};
