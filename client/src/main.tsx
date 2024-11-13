import { StrictMode } from 'react';
import { createRoot } from 'react-dom/client';
import toast from 'react-hot-toast';
import { createBrowserRouter, RouterProvider } from 'react-router-dom';
import { App } from './App';
import { ProductsPage } from './pages/products/Products';

const router = createBrowserRouter([
    {
        path: '/',
        element: <App />,
    },
    {
        path: '/products',
        element: <ProductsPage />,
    },
    {
        path: '*',
        element: <h1>page not found</h1>,
    },
]);

const origFetch = window.fetch;

export const fetchWrapper = async (...args) => {
    console.log('reqreq');
    const response = await origFetch(...args);
    console.log('asdfasdfsadfasdf');
    if (response.status >= 400) {
        const text = await response.text();
        toast.error(text.toString());
    }
    return response;
};

createRoot(document.getElementById('root')!).render(
    <StrictMode>
        <RouterProvider router={router} />
    </StrictMode>
);
