import { API_ENDPOINT } from '@/App';
import { products } from '@/pages/products/store';
import { useUnit } from 'effector-react';
import { useEffect, useState } from 'react';
import Popup from 'reactjs-popup';
import styles from './ProductsTable.module.css';

export const CreateProductPopup: React.FC = () => {
    const [popupOpen, setPopupOpen] = useUnit([
        products.$createPopupOpen,
        products.setPopupPropsEv,
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
        fetch(`${API_ENDPOINT}/location/get-all`, {
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
        products.addFx({
            name: eventTarget.name.value,
            cost: eventTarget.cost.value,
            locationId: eventTarget.location.value,
            manufacturerId: eventTarget.manufacturer.value,
            measureUnit: eventTarget.measureUnit.value,
            number: eventTarget.number.value,
            ownerId: eventTarget.owner.value,
            price: eventTarget.price.value,
            rating: eventTarget.rating.value,
        });
        if (error === '') {
            console.log('niggagaga');
        }
    };

    const error = useUnit(products.$error);

    return (
        <>
            <button
                onClick={() => {
                    setPopupOpen(true);
                }}
            >
                Создать
            </button>
            <Popup position="center center" open={popupOpen} onClose={() => setPopupOpen(false)}>
                <form onSubmit={handleSubmit}>
                    <div className={styles.popup}>
                        <div className={styles.textCreate}>Создать продукт</div>
                        <input name="name" placeholder="Название" type="text" />
                        <input name="price" placeholder="Цена" type="text" />
                        <input name="rating" placeholder="Рейтинг" type="text" />
                        <div className={styles.withLabel}>
                            <label>Ед. изм</label>
                            <select name="measureUnit">
                                <option>GRAMS</option>
                                <option>SQUARE_METERS</option>
                                <option>PCS</option>
                            </select>
                        </div>
                        <input name="number" placeholder="Номер" type="text" />
                        <div className={styles.withLabel}>
                            <label>Владелец</label>
                            <select name="owner">
                                {ownerList.map((x) => (
                                    <option key={`owner${x.id}`} value={x.id}>
                                        {x.name} ({x.id})
                                    </option>
                                ))}
                            </select>
                        </div>
                        <div className={styles.withLabel}>
                            <label>Производитель</label>
                            <select name="manufacturer">
                                {manufacturerList.map((x) => (
                                    <option key={`manuf${x.id}`} value={x.id}>
                                        {x.name} ({x.fullName})
                                    </option>
                                ))}
                            </select>
                        </div>
                        <input name="cost" placeholder="Цена производителя" type="text" />
                        <div className={styles.withLabel}>
                            <label>Локация</label>
                            <select name="location">
                                {locationList.map((x) => (
                                    <option key={`location${x.id}`} value={x.id}>
                                        ({x.x}, {x.y})
                                    </option>
                                ))}
                            </select>
                        </div>
                        <button>Создать</button>
                        <div className={styles.error}>{error}</div>
                    </div>
                </form>
            </Popup>
        </>
    );
};
