import { coordinates } from '@/pages/coordinates/store';
import { useUnit } from 'effector-react';
import React from 'react';
import Popup from 'reactjs-popup';
import styles from './Coordinates.module.css';

export const CreateCoordinatesPopup: React.FC = () => {
    const [popupOpen, setPopupOpen] = useUnit([
        coordinates.$createPopupOpen,
        coordinates.setCreatePopupPropsEv,
    ]);

    const handleSubmit = (e: React.FormEvent<HTMLFormElement>) => {
        e.preventDefault();
        const form = e.target as any;
        coordinates.addFx({
            x: Number(form.x.value),
            y: parseFloat(form.y.value),
        });
    };

    const error = useUnit(coordinates.$error);

    return (
        <>
            <button
                onClick={() => {
                    setPopupOpen(true);
                }}
            >
                Create Coordinates
            </button>
            <Popup position="center center" open={popupOpen} onClose={() => setPopupOpen(false)}>
                <form onSubmit={handleSubmit}>
                    <div className={styles.popup}>
                        <div className={styles.textCreate}>Create Coordinates</div>
                        <input name="x" placeholder="X Coordinate" type="number" />
                        <input name="y" placeholder="Y Coordinate" type="number" step="any" />
                        <button>Create</button>
                        <div className={styles.error}>{error}</div>
                    </div>
                </form>
            </Popup>
        </>
    );
};
