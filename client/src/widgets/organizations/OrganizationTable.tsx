import { organizations } from '@/pages/organizations/store';
import { useUnit } from 'effector-react';
import styles from './Organization.module.css';

interface OrganizationsTableProps {
    data: any;
}

export const OrganizationsTable: React.FC<OrganizationsTableProps> = ({ data }) => {
    const setUpdatePopupPropsEv = useUnit(organizations.setUpdatePopupPropsEv);

    return (
        <table className={styles.table}>
            <thead>
                <tr>
                    <th>Name</th>
                    <th>Full Name</th>
                    <th>Annual Turnover</th>
                    <th>Employees Count</th>
                    <th>Official Address</th>
                    <th>Postal Address</th>
                </tr>
            </thead>
            <tbody>
                {data.content.map((x: any) => (
                    <tr key={x.id}>
                        <td>{x.name}</td>
                        <td>{x.fullName}</td>
                        <td>{x.annualTurnover}</td>
                        <td>{x.employeesCount}</td>
                        <td>{x.officialAddress.zipCode}</td>
                        <td>{x.postalAddress.zipCode}</td>
                        <td>
                            {x.editable && (
                                <button
                                    onClick={() => setUpdatePopupPropsEv({ ...x, opened: true })}
                                >
                                    Edit
                                </button>
                            )}
                        </td>
                    </tr>
                ))}
            </tbody>
        </table>
    );
};
