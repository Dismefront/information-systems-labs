import { API_ENDPOINT } from '@/App';
import { products } from '@/pages/products/store';
import { useUnit } from 'effector-react';
import { useEffect, useState } from 'react';
import Popup from 'reactjs-popup';
import styles from './ProductsTable.module.css';

export const UpdateProductPopup: React.FC = () => {
    const [popupOpen, setPopupOpen] = useUnit([
        products.$updatePopupOpen,
        products.setUpdatePopupPropsEv,
    ]);
    const [manufacturerList, updateManufacturerList] = useState<any[]>([]);
    const [ownerList, updateOwnerList] = useState<any[]>([]);
    const [locationList, updateLocationList] = useState<any[]>([]);
    useEffect(() => {
        fetch(`${API_ENDPOINT}/organization/get-all`, {
            method: 'get',
            credentials: 'include',
            headers: {
                'Content-Type': 'application/json',
            },
        })
            .then((res) => res.json())
            .then((data) => updateManufacturerList(data));
        fetch(`${API_ENDPOINT}/coordinates/get-all`, {
            method: 'get',
            credentials: 'include',
            headers: {
                'Content-Type': 'application/json',
            },
        })
            .then((res) => res.json())
            .then((data) => updateLocationList(data));
        fetch(`${API_ENDPOINT}/person/get-all`, {
            method: 'get',
            credentials: 'include',
            headers: {
                'Content-Type': 'application/json',
            },
        })
            .then((res) => res.json())
            .then((data) => updateOwnerList(data));
    }, []);

    const handleSubmit = (e: React.FormEvent<HTMLFormElement>) => {
        e.preventDefault();
        const eventTarget = e.target as any;
        products.updateFx({
            name: eventTarget.name.value,
            cost: eventTarget.cost.value,
            locationId: eventTarget.location.value,
            manufacturerId: eventTarget.manufacturer.value,
            measureUnit: eventTarget.measureUnit.value,
            number: eventTarget.number.value,
            ownerId: eventTarget.owner.value,
            price: eventTarget.price.value,
            rating: eventTarget.rating.value,
            id: popupOpen.id!,
            opened: true,
        });
    };

    const error = useUnit(products.$error);

    return (
        <>
            <Popup
                position="center center"
                open={popupOpen?.opened}
                onClose={() => setPopupOpen({ opened: false })}
            >
                <form onSubmit={handleSubmit}>
                    <div className={styles.popup}>
                        <div className={styles.textCreate}>Обновить продукт</div>
                        <input
                            name="name"
                            placeholder="Название"
                            type="text"
                            defaultValue={popupOpen.name}
                        />
                        <input
                            name="price"
                            placeholder="Цена"
                            type="text"
                            defaultValue={popupOpen.price}
                        />
                        <input
                            name="rating"
                            placeholder="Рейтинг"
                            type="text"
                            defaultValue={popupOpen.rating}
                        />
                        <div className={styles.withLabel}>
                            <label>Ед. изм</label>
                            <select name="measureUnit" defaultValue={popupOpen.measureUnit}>
                                <option>GRAMS</option>
                                <option>SQUARE_METERS</option>
                                <option>PCS</option>
                            </select>
                        </div>
                        <input
                            name="number"
                            placeholder="Номер"
                            type="text"
                            defaultValue={popupOpen.number}
                        />
                        <div className={styles.withLabel}>
                            <label>Владелец</label>
                            <select name="owner" defaultValue={popupOpen.ownerId}>
                                {ownerList.map((x) => (
                                    <option key={`owner${x.id}`} value={x.id}>
                                        {x.name} ({x.id})
                                    </option>
                                ))}
                            </select>
                        </div>
                        <div className={styles.withLabel}>
                            <label>Производитель</label>
                            <select name="manufacturer" defaultValue={popupOpen.manufacturerId}>
                                {manufacturerList.map((x) => (
                                    <option key={`manuf${x.id}`} value={x.id}>
                                        {x.name} ({x.fullName})
                                    </option>
                                ))}
                            </select>
                        </div>
                        <input
                            name="cost"
                            placeholder="Цена производителя"
                            type="text"
                            defaultValue={popupOpen.cost}
                        />
                        <div className={styles.withLabel}>
                            <label>Локация</label>
                            <select name="location" defaultValue={popupOpen.locationId}>
                                {locationList.map((x) => (
                                    <option key={`location${x.id}`} value={x.id}>
                                        ({x.x}, {x.y})
                                    </option>
                                ))}
                            </select>
                        </div>
                        <button>Обновить</button>
                        <button
                            onClick={() => {
                                products.deleteFx({
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
