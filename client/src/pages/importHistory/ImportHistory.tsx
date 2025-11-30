import { NavBar } from '@/widgets/navbar/NavBar';
import { importHistory } from './store';
import { useUnit } from 'effector-react';
import { useEffect, useState } from 'react';
import { $currentUser } from '@/shared/auth-controller/auth';
import { API_ENDPOINT } from '@/App';

export const ImportHistory: React.FC = () => {
    const data = useUnit(importHistory.$data);
    const user = useUnit($currentUser);
    const [anythingShown, setAnythingShown] = useState(false);
    useEffect(() => {
        importHistory.fetchFx();
    }, []);
    return (
        <>
            <NavBar />
            {data === null ? (
                'Loading...'
            ) : (
                <div style={{ margin: '20px' }}>
                    {(data.length === 0 || !anythingShown) && 'История пуста'}
                    {data.length === 0 && <h1>История импортов</h1>}
                    {data.map((x) => {
                        if (user?.roles.includes('ROLE_ADMIN')) {
                            !anythingShown && setAnythingShown(true);
                            return (
                                <>
                                    id: {x.id},&emsp; obj_cnt: {x.objectCount},&emsp; status:{' '}
                                    {x.status}
                                    ,&emsp; by: {x.user.username},&emsp; at: {x.timestamp}&emsp;
                                    {x.storageKey && (
                                        <a
                                            href={`${API_ENDPOINT}/product/download/${x.storageKey}`}
                                            download
                                        >
                                            download
                                        </a>
                                    )}
                                    <br />
                                </>
                            );
                        } else if (x.user.username === user?.username) {
                            !anythingShown && setAnythingShown(true);
                            return (
                                <>
                                    id: {x.id},&emsp; obj_cnt: {x.objectCount},&emsp; status:{' '}
                                    {x.status},&emsp; at: {x.timestamp}&emsp;
                                    {x.storageKey && (
                                        <a
                                            href={`${API_ENDPOINT}/product/download/${x.storageKey}`}
                                            download
                                        >
                                            download
                                        </a>
                                    )}
                                    <br />
                                </>
                            );
                        }
                    })}
                </div>
            )}
        </>
    );
};
