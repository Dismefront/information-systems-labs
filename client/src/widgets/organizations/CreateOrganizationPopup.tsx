import { API_ENDPOINT } from '@/App';
import { organizations } from '@/pages/organizations/store';
import { useUnit } from 'effector-react';
import { useEffect, useState } from 'react';
import Popup from 'reactjs-popup';
import styles from './Organization.module.css';

export const CreateOrganizationPopup: React.FC = () => {
    const [popupOpen, setPopupOpen] = useUnit([
        organizations.$createPopupOpen,
        organizations.setCreatePopupPropsEv,
    ]);
    const [addressList, setAddressList] = useState<any[]>([]);

    useEffect(() => {
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
        organizations.addFx({
            name: form.name.value,
            officialAddressId: form.officialAddressId.value,
            annualTurnover: Number(form.annualTurnover.value),
            employeesCount: Number(form.employeesCount.value),
            fullName: form.fullName.value,
            postalAddressId: form.postalAddressId.value,
        });
    };

    const error = useUnit(organizations.$error);

    return (
        <>
            <button onClick={() => setPopupOpen(true)}>Create Organization</button>
            <Popup position="center center" open={popupOpen} onClose={() => setPopupOpen(false)}>
                <form onSubmit={handleSubmit}>
                    <div className={styles.popup}>
                        <div className={styles.textCreate}>Создать организацию</div>
                        <input name="name" placeholder="Name" type="text" />
                        <input name="fullName" placeholder="Full Name" type="text" />
                        <input name="annualTurnover" placeholder="Annual Turnover" type="number" />
                        <input name="employeesCount" placeholder="Employees Count" type="number" />
                        <select name="officialAddressId">
                            {addressList.map((x) => (
                                <option key={x.id} value={x.id}>
                                    {x.zipCode}
                                </option>
                            ))}
                        </select>
                        <select name="postalAddressId">
                            {addressList.map((x) => (
                                <option key={x.id} value={x.id}>
                                    {x.zipCode}
                                </option>
                            ))}
                        </select>
                        <button>Create</button>
                        <div>{error}</div>
                    </div>
                </form>
            </Popup>
        </>
    );
};
