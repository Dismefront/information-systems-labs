import { locations } from '@/pages/locations/store';
import { useUnit } from 'effector-react';
import React from 'react';
import Popup from 'reactjs-popup';
import styles from './Location.module.css';

export const UpdateLocationPopup: React.FC = () => {
    const [popupOpen, setPopupOpen] = useUnit([
        locations.$updatePopupOpen,
        locations.setUpdatePopupPropsEv,
    ]);

    const handleSubmit = (e: React.FormEvent<HTMLFormElement>) => {
        e.preventDefault();
        const form = e.target as any;
        locations.updateFx({
            id: popupOpen.id!,
            x: Number(form.x.value),
            y: parseFloat(form.y.value),
            z: parseFloat(form.z.value),
            opened: true,
        });
    };

    const error = useUnit(locations.$error);

    return (
        <Popup
            position="center center"
            open={popupOpen.opened}
            onClose={() => setPopupOpen({ opened: false })}
        >
            <form onSubmit={handleSubmit}>
                <div className={styles.popup}>
                    <div className={styles.textCreate}>Update Location</div>
                    <input
                        defaultValue={popupOpen.x}
                        name="x"
                        placeholder="X Coordinate (long)"
                        type="number"
                    />
                    <input
                        defaultValue={popupOpen.y}
                        name="y"
                        placeholder="Y Coordinate (double)"
                        type="number"
                        step="any"
                    />
                    <input
                        defaultValue={popupOpen.z}
                        name="z"
                        placeholder="Z Coordinate (float)"
                        type="number"
                        step="any"
                    />
                    <button>Update</button>
                    <div className={styles.error}>{error}</div>
                </div>
            </form>
        </Popup>
    );
};
