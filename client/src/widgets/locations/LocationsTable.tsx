import { locations } from '@/pages/locations/store';
import { useUnit } from 'effector-react';
import React from 'react';
import styles from './Location.module.css';

interface LocationsTableProps {
    data: any;
}

export const LocationsTable: React.FC<LocationsTableProps> = ({ data }) => {
    const setUpdatePopupPropsEv = useUnit(locations.setUpdatePopupPropsEv);

    return (
        <table className={styles.table}>
            <thead>
                <tr>
                    <th>X Coordinate</th>
                    <th>Y Coordinate</th>
                    <th>Z Coordinate</th>
                </tr>
            </thead>
            <tbody>
                {data.content.map((x: any, idx: number) => (
                    <tr key={x.id}>
                        <td>{x.x}</td>
                        <td>{x.y}</td>
                        <td>{x.z}</td>
                        <td>
                            <button
                                onClick={() => {
                                    setUpdatePopupPropsEv({
                                        opened: true,
                                        id: x.id,
                                        x: x.x,
                                        y: x.y,
                                        z: x.z,
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
