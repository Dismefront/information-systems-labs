import { API_ENDPOINT } from '@/App';
import { persons } from '@/pages/persons/store';
import { useUnit } from 'effector-react';
import { useEffect, useState } from 'react';
import Popup from 'reactjs-popup';
import styles from './Persons.module.css';

export const UpdatePersonPopup: React.FC = () => {
    const [popupOpen, setPopupOpen] = useUnit([
        persons.$updatePopupOpen,
        persons.setUpdatePopupPropsEv,
    ]);
    const [locationList, updateLocationList] = useState<any[]>([]);
    useEffect(() => {
        fetch(`${API_ENDPOINT}/location/get-all`, {
            method: 'get',
            credentials: 'include',
            headers: {
                'Content-Type': 'application/json',
            },
        })
            .then((res) => res.json())
            .then((data) => updateLocationList(data));
    }, []);

    const handleSubmit = (e: React.FormEvent<HTMLFormElement>) => {
        e.preventDefault();
        const eventTarget = e.target as any;
        persons.updateFx({
            name: eventTarget.name.value,
            locationId: eventTarget.location.value,
            eyeColor: eventTarget.eyeColor.value,
            hairColor: eventTarget.hairColor.value,
            height: eventTarget.height.value,
            nationality: eventTarget.nationality.value,
            id: popupOpen.id!,
            opened: true,
        });
    };

    const error = useUnit(persons.$error);

    return (
        <>
            <Popup
                position="center center"
                open={popupOpen.opened}
                onClose={() => setPopupOpen({ opened: false })}
            >
                <form onSubmit={handleSubmit}>
                    <div className={styles.popup}>
                        <div className={styles.textCreate}>Обновить человека</div>
                        <input
                            defaultValue={popupOpen.name}
                            name="name"
                            placeholder="Имя"
                            type="text"
                        />
                        <div className={styles.withLabel}>
                            <label>Цвет глаз</label>
                            <select defaultValue={popupOpen.eyeColor} name="eyeColor">
                                <option>GREEN</option>
                                <option>RED</option>
                                <option>BLACK</option>
                                <option>YELLOW</option>
                            </select>
                        </div>
                        <div className={styles.withLabel}>
                            <label>Цвет волос</label>
                            <select defaultValue={popupOpen.hairColor} name="hairColor">
                                <option>GREEN</option>
                                <option>RED</option>
                                <option>BLACK</option>
                                <option>YELLOW</option>
                            </select>
                        </div>
                        <input
                            defaultValue={popupOpen.height}
                            name="height"
                            placeholder="Рост"
                            type="text"
                        />
                        <div className={styles.withLabel}>
                            <label>Страна</label>
                            <select defaultValue={popupOpen.nationality} name="nationality">
                                <option>UNITED_KINGDOM</option>
                                <option>SPAIN</option>
                                <option>ITALY</option>
                                <option>JAPAN</option>
                            </select>
                        </div>
                        <div className={styles.withLabel}>
                            <label>Локация</label>
                            <select defaultValue={popupOpen.locationId} name="location">
                                {locationList.map((x) => (
                                    <option key={`location${x.id}`} value={x.id}>
                                        ({x.x}, {x.y}, {x.z})
                                    </option>
                                ))}
                            </select>
                        </div>
                        <button>Обновить</button>
                        <button
                            onClick={() => {
                                persons.deleteFx({
                                    id: popupOpen.id!,
                                });
                                setPopupOpen({ opened: false });
                            }}
                            type="button"
                        >
                            Удалить
                        </button>
                        <div className={styles.error}>{error}</div>
                    </div>
                </form>
            </Popup>
        </>
    );
};
