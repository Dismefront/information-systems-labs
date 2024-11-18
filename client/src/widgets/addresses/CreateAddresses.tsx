import { API_ENDPOINT } from '@/App';
import { address } from '@/pages/addresses/store';
import { useUnit } from 'effector-react';
import React, { useEffect, useState } from 'react';
import Popup from 'reactjs-popup';
import styles from './Address.module.css';

export const CreateAddressPopup: React.FC = () => {
    const [popupOpen, setPopupOpen] = useUnit([
        address.$createPopupOpen,
        address.setCreatePopupPropsEv,
    ]);

    const handleSubmit = (e: React.FormEvent<HTMLFormElement>) => {
        e.preventDefault();
        const form = e.target as any;
        address.addFx({
            zipCode: form.zipCode.value,
            townId: form.town.value,
        });
    };

    const [addressList, updateAddressList] = useState<any[]>([]);

    useEffect(() => {
        fetch(`${API_ENDPOINT}/location/get-all`, {
            method: 'get',
            credentials: 'include',
            headers: {
                'Content-Type': 'application/json',
            },
        })
            .then((res) => res.json())
            .then((data) => updateAddressList(data));
    }, []);

    const error = useUnit(address.$error);

    return (
        <>
            <button
                onClick={() => {
                    setPopupOpen(true);
                }}
            >
                Create Address
            </button>
            <Popup position="center center" open={popupOpen} onClose={() => setPopupOpen(false)}>
                <form onSubmit={handleSubmit}>
                    <div className={styles.popup}>
                        <div className={styles.textCreate}>Create Address</div>
                        <div className={styles.withLabel}>
                            <label>Town</label>
                            <select name="town">
                                {addressList.map((x) => (
                                    <option key={x.id} value={x.id}>
                                        ({x.x}, {x.y}, {x.z})
                                    </option>
                                ))}
                            </select>
                        </div>
                        <input name="zipCode" placeholder="ZipCode" type="text" />
                        <button>Create</button>
                        <div className={styles.error}>{error}</div>
                    </div>
                </form>
            </Popup>
        </>
    );
};
