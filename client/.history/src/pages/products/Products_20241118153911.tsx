import { NavBar } from '@/widgets/navbar/NavBar';
import { Pagination } from '@/widgets/pagination/Pagination';
import { CreateProductPopup } from '@/widgets/products/CreateProductPopup';
import { ProductsTable } from '@/widgets/products/ProductsTable';
import { UpdateProductPopup } from '@/widgets/products/UpdateProductPopup';
import { useUnit } from 'effector-react';
import { useEffect } from 'react';
import { useParams } from 'react-router-dom';
import { products } from './store';
import { API_ENDPOINT } from '@/App';
import { SpecialCases } from './SpecialCases';

export const ProductsPage: React.FC = () => {
    const data = useUnit(products.$data);
    const params: { page: string } = useParams() as any;
    useEffect(() => {
        products.fetchFx({
            page: Number(params.page) - 1,
            size: 10,
        });
    }, [params.page]);
    return (
        <>
            <NavBar />
            {data === null ? (
                'Загрузка'
            ) : (
                <>
                    <CreateProductPopup />
                    <UpdateProductPopup />
                    <ProductsTable data={data} />
                    <Pagination
                        path="/products"
                        totalPages={data.totalPages}
                        currentPage={data.number + 1}
                    />
                    <SpecialCases />
                </>
            )}
        </>
    );
};
