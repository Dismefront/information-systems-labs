import { StrictMode } from 'react';
import { createRoot } from 'react-dom/client';
import { Toaster } from 'react-hot-toast';
import { createBrowserRouter, RouterProvider } from 'react-router-dom';
import { App } from './App';
import { LoginPage } from './pages/login/LoginPage';
import { ProductsPage } from './pages/products/Products';
import { AuthKeeper } from './shared/auth-controller/AuthKeeper';
import { fetchWrapper } from './shared/fetch-wrapper/fetch-wrapper';
import { LogoutHandler } from './shared/logout/LogoutHandler';

window.fetch = fetchWrapper;

const router = createBrowserRouter([
    {
        path: '/',
        element: <App />,
    },
    {
        path: '/products/:page',
        element: (
            <AuthKeeper>
                <ProductsPage />
            </AuthKeeper>
        ),
    },
    {
        path: '/login',
        element: <LoginPage />,
    },
    {
        path: '/logout',
        element: (
            <AuthKeeper>
                <LogoutHandler />
            </AuthKeeper>
        ),
    },
    {
        path: '*',
        element: <h1>page not found</h1>,
    },
]);

createRoot(document.getElementById('root')!).render(
    <StrictMode>
        <Toaster position="top-right" />
        <RouterProvider router={router} />
    </StrictMode>
);
