import { API_ENDPOINT } from '@/App';
import { organizations } from '@/pages/organizations/store';
import { useUnit } from 'effector-react';
import { useEffect, useState } from 'react';
import Popup from 'reactjs-popup';
import styles from './Organization.module.css';

export const UpdateOrganizationPopup: React.FC = () => {
    const [popupOpen, setPopupOpen] = useUnit([
        organizations.$updatePopupOpen,
        organizations.setUpdatePopupPropsEv,
    ]);
    const [addressList, setAddressList] = useState<any[]>([]);

    useEffect(() => {
        // Fetch available addresses for selection
        fetch(`${API_ENDPOINT}/address/get-all`, {
            method: 'get',
            credentials: 'include',
            headers: {
                'Content-Type': 'application/json',
            },
        })
            .then((res) => res.json())
            .then((data) => setAddressList(data));
    }, []);

    const handleSubmit = (e: React.FormEvent<HTMLFormElement>) => {
        e.preventDefault();
        const form = e.target as any;
        organizations.updateFx({
            id: popupOpen.id!,
            name: form.name.value,
            fullName: form.fullName.value,
            annualTurnover: Number(form.annualTurnover.value),
            employeesCount: Number(form.employeesCount.value),
            officialAddressId: form.officialAddressId.value,
            postalAddressId: form.postalAddressId.value,
            opened: true,
        });
    };

    const error = useUnit(organizations.$error);

    return (
        <Popup
            position="center center"
            open={popupOpen.opened}
            onClose={() => setPopupOpen({ opened: false })}
        >
            <form onSubmit={handleSubmit}>
                <div className={styles.popup}>
                    <div className={styles.textUpdate}>Update Organization</div>
                    <input
                        defaultValue={popupOpen.name}
                        name="name"
                        placeholder="Name"
                        type="text"
                    />
                    <input
                        defaultValue={popupOpen.fullName}
                        name="fullName"
                        placeholder="Full Name"
                        type="text"
                    />
                    <input
                        defaultValue={popupOpen.annualTurnover}
                        name="annualTurnover"
                        placeholder="Annual Turnover"
                        type="number"
                    />
                    <input
                        defaultValue={popupOpen.employeesCount}
                        name="employeesCount"
                        placeholder="Employees Count"
                        type="number"
                    />
                    <div className={styles.withLabel}>
                        <label>Official Address</label>
                        <select defaultValue={popupOpen.officialAddressId} name="officialAddressId">
                            {addressList.map((x) => (
                                <option key={x.id} value={x.id}>
                                    {x.zipCode}
                                </option>
                            ))}
                        </select>
                    </div>
                    <div className={styles.withLabel}>
                        <label>Postal Address</label>
                        <select defaultValue={popupOpen.postalAddressId} name="postalAddressId">
                            {addressList.map((x) => (
                                <option key={x.id} value={x.id}>
                                    {x.zipCode}
                                </option>
                            ))}
                        </select>
                    </div>
                    <button>Update</button>
                    <button
                        onClick={() => {
                            organizations.deleteFx({
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
    );
};
