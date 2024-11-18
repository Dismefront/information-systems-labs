import { createRoot } from 'react-dom/client';
import { Toaster } from 'react-hot-toast';
import { createBrowserRouter, RouterProvider } from 'react-router-dom';
import { App } from './App';
import { AddressPage } from './pages/addresses/Addresses';
import { AdminRequests } from './pages/admin-requests/AdminRequests';
import { CoordinatesPage } from './pages/coordinates/CoordinatesPage';
import { LocationsPage } from './pages/locations/Locations';
import { LoginPage } from './pages/login/LoginPage';
import { OrganizationsPage } from './pages/organizations/Organizations';
import { PersonsPage } from './pages/persons/Persons';
import { ProductsPage } from './pages/products/Products';
import { AuthKeeper } from './shared/auth-controller/AuthKeeper';
import { BecomeAdmin } from './shared/become-admin/BecomeAdmin';
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
        path: '/people/:page',
        element: (
            <AuthKeeper>
                <PersonsPage />
            </AuthKeeper>
        ),
    },
    {
        path: '/organizations/:page',
        element: (
            <AuthKeeper>
                <OrganizationsPage />
            </AuthKeeper>
        ),
    },
    {
        path: '/locations/:page',
        element: (
            <AuthKeeper>
                <LocationsPage />
            </AuthKeeper>
        ),
    },
    {
        path: '/coordinates/:page',
        element: (
            <AuthKeeper>
                <CoordinatesPage />
            </AuthKeeper>
        ),
    },
    {
        path: '/addresses/:page',
        element: (
            <AuthKeeper>
                <AddressPage />
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
        path: '/admin-requests',
        element: (
            <AuthKeeper adminRoute>
                <AdminRequests />
            </AuthKeeper>
        ),
    },
    {
        path: '/become-admin',
        element: (
            <AuthKeeper>
                <BecomeAdmin />
            </AuthKeeper>
        ),
    },
    {
        path: '*',
        element: <h1>page not found</h1>,
    },
]);

createRoot(document.getElementById('root')!).render(
    <>
        <Toaster position="top-right" />
        <RouterProvider router={router} />
    </>
);
