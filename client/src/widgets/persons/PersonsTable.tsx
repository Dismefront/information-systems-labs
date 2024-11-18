import { persons } from '@/pages/persons/store';
import { useUnit } from 'effector-react';
import styles from './Persons.module.css';

interface PersonsTableProps {
    data: any;
}

export const PersonsTable: React.FC<PersonsTableProps> = ({ data }) => {
    const setUpdatePopupPropsEv = useUnit(persons.setUpdatePopupPropsEv);
    return (
        <table className={styles.table}>
            <thead>
                <tr>
                    <th>Имя</th>
                    <th>Цвет глаз</th>
                    <th>Цвет волос</th>
                    <th>Рост</th>
                    <th>Страна</th>
                    <th>Локация</th>
                </tr>
            </thead>
            <tbody>
                {data.content.map((x: any, idx: number) => {
                    return (
                        <tr key={`${x.name}${idx}`}>
                            <td>{x.name}</td>
                            <td>{x.eyeColor}</td>
                            <td>{x.hairColor}</td>
                            <td>{x.height}</td>
                            <td>{x.nationality}</td>
                            <td>{`(${x.location.x}, ${x.location.y}, ${x.location.z})`}</td>
                            {x.editable && (
                                <td>
                                    <button
                                        onClick={() => {
                                            console.log(x.id);
                                            setUpdatePopupPropsEv({
                                                opened: true,
                                                id: x.id,
                                                name: x.name,
                                                locationId: x.locationId,
                                                eyeColor: x.eyeColor,
                                                hairColor: x.hairColor,
                                                height: x.height,
                                                nationality: x.nationality,
                                            });
                                        }}
                                    >
                                        edit
                                    </button>
                                </td>
                            )}
                        </tr>
                    );
                })}
            </tbody>
        </table>
    );
};
