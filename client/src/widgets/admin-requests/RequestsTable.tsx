import { adminRequests } from '@/pages/admin-requests/store';
import styles from './RequestsTable.module.css';

interface RequestsTableProps {
    data: any[];
}

export const RequestsTable: React.FC<RequestsTableProps> = ({ data }) => {
    return (
        <div className={styles.table}>
            {data.map((x) => (
                <div className={styles.request} key={`userReq${x.username}`}>
                    <div>{x.username}</div>
                    <button
                        onClick={() => {
                            adminRequests
                                .acceptFx({
                                    id: x.id,
                                })
                                .then(() => adminRequests.fetchFx());
                        }}
                    >
                        Принять
                    </button>
                    <button
                        onClick={() => {
                            adminRequests
                                .declineFx({
                                    id: x.id,
                                })
                                .then(() => adminRequests.fetchFx());
                        }}
                    >
                        Отклонить
                    </button>
                </div>
            ))}
        </div>
    );
};
