import { $currentUser, getCurrentUserEv } from '@/shared/auth-controller/auth';
import { useAuth } from '@/shared/auth-controller/useAuth';
import { useUnit } from 'effector-react';
import { NavLink, NavLinkRenderProps } from 'react-router-dom';
import styles from './NavBar.module.css';

export const NavBar: React.FC = () => {
    const activeHandler = ({ isActive }: NavLinkRenderProps) => {
        return isActive ? styles.active : undefined;
    };
    const currentUser = useUnit($currentUser);
    const isAuthenticated = useAuth();
    if (currentUser === null && isAuthenticated) {
        getCurrentUserEv();
    }
    return (
        <section className={styles.container}>
            <nav className={styles.pages}>
                <NavLink to="/" className={activeHandler}>
                    Home
                </NavLink>
                <NavLink to="/products/1" className={activeHandler}>
                    Products
                </NavLink>
                <NavLink to="/people" className={activeHandler}>
                    People
                </NavLink>
                <NavLink to="/organizations" className={activeHandler}>
                    Organizations
                </NavLink>
                <NavLink to="/locations" className={activeHandler}>
                    Locations
                </NavLink>
                <NavLink to="/coordinates" className={activeHandler}>
                    Coordinates
                </NavLink>
                <NavLink to="/addresses" className={activeHandler}>
                    Addresses
                </NavLink>
            </nav>
            <nav className={styles.additional}>
                {isAuthenticated ? (
                    <NavLink to="/logout" className={activeHandler}>
                        Log out ({currentUser?.username})
                    </NavLink>
                ) : (
                    <NavLink to="/login" className={activeHandler}>
                        Log in
                    </NavLink>
                )}
            </nav>
        </section>
    );
};
