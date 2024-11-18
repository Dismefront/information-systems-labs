import { coordinates } from '@/pages/coordinates/store';
import { useUnit } from 'effector-react';
import React from 'react';
import Popup from 'reactjs-popup';
import styles from './Coordinates.module.css';

export const UpdateCoordinatesPopup: React.FC = () => {
    const [popupOpen, setPopupOpen] = useUnit([
        coordinates.$updatePopupOpen,
        coordinates.setUpdatePopupPropsEv,
    ]);

    const handleSubmit = (e: React.FormEvent<HTMLFormElement>) => {
        e.preventDefault();
        const form = e.target as any;
        coordinates.updateFx({
            id: popupOpen.id!,
            x: Number(form.x.value),
            y: parseFloat(form.y.value),
            opened: true,
        });
    };

    const error = useUnit(coordinates.$error);

    return (
        <>
            <Popup
                position="center center"
                open={popupOpen.opened}
                onClose={() => setPopupOpen({ opened: false })}
            >
                <form onSubmit={handleSubmit}>
                    <div className={styles.popup}>
                        <div className={styles.textCreate}>Update Coordinates</div>
                        <input
                            name="x"
                            defaultValue={popupOpen.x}
                            placeholder="X Coordinate"
                            type="number"
                        />
                        <input
                            name="y"
                            defaultValue={popupOpen.y}
                            placeholder="Y Coordinate"
                            type="number"
                            step="any"
                        />
                        <button>Update</button>
                        <div className={styles.error}>{error}</div>
                    </div>
                </form>
            </Popup>
        </>
    );
};
