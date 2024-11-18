import { address } from '@/pages/addresses/store';
import { useUnit } from 'effector-react';
import React from 'react';
import styles from './Address.module.css';

interface AddressTableProps {
    data: any;
}

export const AddressTable: React.FC<AddressTableProps> = ({ data }) => {
    const setUpdatePopupPropsEv = useUnit(address.setUpdatePopupPropsEv);

    return (
        <table className={styles.table}>
            <thead>
                <tr>
                    <th>Location</th>
                    <th>ZipCode</th>
                </tr>
            </thead>
            <tbody>
                {data.content.map((x: any) => (
                    <tr key={x.id}>
                        <td>
                            ({x.town.x}, {x.town.y}, {x.town.z})
                        </td>
                        <td>{x.zipCode}</td>
                        <td>
                            <button
                                onClick={() => {
                                    setUpdatePopupPropsEv({
                                        opened: true,
                                        id: x.id,
                                        townId: x.town.id,
                                        zipCode: x.zipCode,
                                    });
                                }}
                            >
                                Edit
                            </button>
                        </td>
                    </tr>
                ))}
            </tbody>
        </table>
    );
};
