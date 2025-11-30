import { coordinates } from '@/pages/coordinates/store';
import { useUnit } from 'effector-react';
import React from 'react';
import styles from './Coordinates.module.css';

interface CoordinatesTableProps {
    data: any;
}

export const CoordinatesTable: React.FC<CoordinatesTableProps> = ({ data }) => {
    const setUpdatePopupPropsEv = useUnit(coordinates.setUpdatePopupPropsEv);

    return (
        <table className={styles.table}>
            <thead>
                <tr>
                    <th>X Coordinate</th>
                    <th>Y Coordinate</th>
                </tr>
            </thead>
            <tbody>
                {data.content.map((x: any, idx: number) => (
                    <tr key={x.id}>
                        <td>{x.x}</td>
                        <td>{x.y}</td>
                        <td>
                            {x.editable && (
                                <button
                                    onClick={() => {
                                        setUpdatePopupPropsEv({
                                            opened: true,
                                            id: x.id,
                                            x: x.x,
                                            y: x.y,
                                        });
                                    }}
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
