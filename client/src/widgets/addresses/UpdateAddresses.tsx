import { API_ENDPOINT } from '@/App';
import { address } from '@/pages/addresses/store';
import { useUnit } from 'effector-react';
import React, { useEffect, useState } from 'react';
import Popup from 'reactjs-popup';
import styles from './Address.module.css';

export const UpdateAddressPopup: React.FC = () => {
    const [popupOpen, setPopupOpen] = useUnit([
        address.$updatePopupOpen,
        address.setUpdatePopupPropsEv,
    ]);

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

    const handleSubmit = (e: React.FormEvent<HTMLFormElement>) => {
        e.preventDefault();
        const form = e.target as any;
        address.updateFx({
            id: popupOpen.id!,
            townId: form.town.value,
            zipCode: form.zipCode.value,
            opened: true,
        });
    };

    const error = useUnit(address.$error);

    return (
        <>
            <Popup
                position="center center"
                open={popupOpen.opened}
                onClose={() => setPopupOpen({ opened: false })}
            >
                <form onSubmit={handleSubmit}>
                    <div className={styles.popup}>
                        <div className={styles.textCreate}>Update Address</div>
                        <div className={styles.withLabel}>
                            <label>Town</label>
                            <select defaultValue={popupOpen.townId} name="town">
                                {addressList.map((x) => (
                                    <option key={x.id} value={x.id}>
                                        ({x.x}, {x.y}, {x.z})
                                    </option>
                                ))}
                            </select>
                        </div>
                        <input
                            name="zipCode"
                            defaultValue={popupOpen.zipCode}
                            placeholder="ZipCode"
                            type="text"
                        />
                        <button>Update</button>
                        <div className={styles.error}>{error}</div>
                    </div>
                </form>
            </Popup>
        </>
    );
};
