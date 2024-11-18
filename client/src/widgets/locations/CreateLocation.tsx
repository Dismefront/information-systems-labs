import { locations } from '@/pages/locations/store';
import { useUnit } from 'effector-react';
import React from 'react';
import Popup from 'reactjs-popup';
import styles from './Location.module.css';

export const CreateLocationPopup: React.FC = () => {
    const [popupOpen, setPopupOpen] = useUnit([
        locations.$createPopupOpen,
        locations.setCreatePopupPropsEv,
    ]);

    const handleSubmit = (e: React.FormEvent<HTMLFormElement>) => {
        e.preventDefault();
        const form = e.target as any;
        locations.addFx({
            x: Number(form.x.value),
            y: parseFloat(form.y.value),
            z: parseFloat(form.z.value),
        });
    };

    const error = useUnit(locations.$error);

    return (
        <>
            <button
                onClick={() => {
                    setPopupOpen(true);
                }}
            >
                Create Location
            </button>
            <Popup position="center center" open={popupOpen} onClose={() => setPopupOpen(false)}>
                <form onSubmit={handleSubmit}>
                    <div className={styles.popup}>
                        <div className={styles.textCreate}>Create Location</div>
                        <input name="x" placeholder="X Coordinate (long)" type="number" />
                        <input
                            name="y"
                            placeholder="Y Coordinate (double)"
                            type="number"
                            step="any"
                        />
                        <input
                            name="z"
                            placeholder="Z Coordinate (float)"
                            type="number"
                            step="any"
                        />
                        <button>Create</button>
                        <div className={styles.error}>{error}</div>
                    </div>
                </form>
            </Popup>
        </>
    );
};
