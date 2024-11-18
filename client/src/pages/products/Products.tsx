import { NavBar } from '@/widgets/navbar/NavBar';
import { Pagination } from '@/widgets/pagination/Pagination';
import { CreateProductPopup } from '@/widgets/products/CreateProductPopup';
import { ProductsTable } from '@/widgets/products/ProductsTable';
import { useUnit } from 'effector-react';
import { useEffect } from 'react';
import { useParams } from 'react-router-dom';
import { products } from './store';

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
                    <ProductsTable data={data} />
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
