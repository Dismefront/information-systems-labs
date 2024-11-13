import { StrictMode } from 'react';
import { createRoot } from 'react-dom/client';
import { createBrowserRouter, RouterProvider } from 'react-router-dom';
import { App } from './App';
import { ProductsPage } from './pages/products/Products';
import { LogoutHandler } from './shared/logout/LogoutHandler';

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
        path: '/logout',
        element: <LogoutHandler />,
    },
    {
        path: '*',
        element: <h1>page not found</h1>,
    },
]);

createRoot(document.getElementById('root')!).render(
    <StrictMode>
        <RouterProvider router={router} />
    </StrictMode>
);
