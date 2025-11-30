import { products } from '@/pages/products/store';
import { useUnit } from 'effector-react';
import styles from './ProductsTable.module.css';

interface ProductsTableProps {
    data: any;
}

export const ProductsTable: React.FC<ProductsTableProps> = ({ data }) => {
    const setUpdatePopupPropsEv = useUnit(products.setUpdatePopupPropsEv);
    const sortProps = useUnit(products.$sortProps);

    return (
        <table className={styles.table}>
            <thead>
                <tr>
                    <th
                        style={{ cursor: 'pointer' }}
                        role="button"
                        onClick={() => {
                            products.updSortProps('creationDate');
                        }}
                    >
                        Дата создания{' '}
                        {sortProps.sortBy === 'creationDate' &&
                            (sortProps.sortDir === 'asc' ? '↑' : '↓')}
                    </th>
                    <th
                        style={{ cursor: 'pointer' }}
                        role="button"
                        onClick={() => {
                            products.updSortProps('name');
                        }}
                    >
                        Название{' '}
                        {sortProps.sortBy === 'name' && (sortProps.sortDir === 'asc' ? '↑' : '↓')}
                    </th>
                    <th
                        style={{ cursor: 'pointer' }}
                        role="button"
                        onClick={() => {
                            products.updSortProps('price');
                        }}
                    >
                        Цена{' '}
                        {sortProps.sortBy === 'price' && (sortProps.sortDir === 'asc' ? '↑' : '↓')}
                    </th>
                    <th
                        style={{ cursor: 'pointer' }}
                        role="button"
                        onClick={() => {
                            products.updSortProps('rating');
                        }}
                    >
                        Рейтинг{' '}
                        {sortProps.sortBy === 'rating' && (sortProps.sortDir === 'asc' ? '↑' : '↓')}
                    </th>
                    <th>ед. изм.</th>
                    <th
                        style={{ cursor: 'pointer' }}
                        role="button"
                        onClick={() => {
                            products.updSortProps('partNumber');
                        }}
                    >
                        Номер{' '}
                        {sortProps.sortBy === 'partNumber' &&
                            (sortProps.sortDir === 'asc' ? '↑' : '↓')}
                    </th>
                    <th>Владелец</th>
                    <th>Производитель</th>
                    <th
                        style={{ cursor: 'pointer' }}
                        role="button"
                        onClick={() => {
                            products.updSortProps('manufactureCost');
                        }}
                    >
                        Цена производства{' '}
                        {sortProps.sortBy === 'manufactureCost' &&
                            (sortProps.sortDir === 'asc' ? '↑' : '↓')}
                    </th>
                    <th>Локация</th>
                </tr>
            </thead>
            <tbody>
                {data.content.map((x: any, idx: number) => {
                    return (
                        <tr key={`${x.name}${idx}`}>
                            <td>{new Date(x.creationDate).toDateString()}</td>
                            <td>{x.name}</td>
                            <td>{x.price}</td>
                            <td>{x.rating}</td>
                            <td>{x.unitOfMeasure}</td>
                            <td>{x.partNumber}</td>
                            <td>{x.owner.name}</td>
                            <td>{x.manufacturer.name}</td>
                            <td>{x.manufactureCost}</td>
                            <td>{`(${x.coordinates.x}, ${x.coordinates.y})`}</td>
                            {x.editable && (
                                <td>
                                    <button
                                        onClick={() => {
                                            setUpdatePopupPropsEv({
                                                id: x.id,
                                                cost: x.manufactureCost,
                                                locationId: x.coordinates.id,
                                                manufacturerId: x.manufacturer.id,
                                                measureUnit: x.unitOfMeasure,
                                                name: x.name,
                                                number: x.partNumber,
                                                opened: true,
                                                ownerId: x.owner.id,
                                                price: x.price,
                                                rating: x.rating,
                                            });
                                        }}
                                    >
                                        edit
                                    </button>
                                </td>
                            )}
                        </tr>
                    );
                })}
            </tbody>
        </table>
    );
};
