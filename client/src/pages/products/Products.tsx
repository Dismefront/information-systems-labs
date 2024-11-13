import { NavBar } from '@/widgets/navbar/NavBar';
import { useEffect } from 'react';
import { products } from './store';

export const ProductsPage: React.FC = () => {
    useEffect(() => {
        products.fetchFx();
    }, []);
    return (
        <>
            <NavBar />
        </>
    );
};
