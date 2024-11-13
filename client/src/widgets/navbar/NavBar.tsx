import { NavLink, NavLinkRenderProps } from 'react-router-dom';
import styles from './NavBar.module.css';

export const NavBar: React.FC = () => {
    const activeHandler = ({ isActive }: NavLinkRenderProps) => {
        return isActive ? styles.active : undefined;
    };
    return (
        <section className={styles.container}>
            <nav className={styles.pages}>
                <NavLink to="/products" className={activeHandler}>
                    Products
                </NavLink>
                <NavLink to="/" className={activeHandler}>
                    People
                </NavLink>
                <NavLink to="/" className={activeHandler}>
                    Organizations
                </NavLink>
                <NavLink to="/" className={activeHandler}>
                    Locations
                </NavLink>
                <NavLink to="/" className={activeHandler}>
                    Coordinates
                </NavLink>
                <NavLink to="/" className={activeHandler}>
                    Addresses
                </NavLink>
            </nav>
            <nav className={styles.additional}>
                <NavLink to="/" className={activeHandler}>
                    Log out
                </NavLink>
            </nav>
        </section>
    );
};
