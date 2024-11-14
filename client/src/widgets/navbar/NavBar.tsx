import { useAuth } from '@/shared/auth-controller/useAuth';
import { NavLink, NavLinkRenderProps } from 'react-router-dom';
import styles from './NavBar.module.css';

export const NavBar: React.FC = () => {
    const activeHandler = ({ isActive }: NavLinkRenderProps) => {
        return isActive ? styles.active : undefined;
    };
    const isAuthenticated = useAuth();
    return (
        <section className={styles.container}>
            <nav className={styles.pages}>
                <NavLink to="/" className={activeHandler}>
                    Home
                </NavLink>
                <NavLink to="/products" className={activeHandler}>
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
                        Log out
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
