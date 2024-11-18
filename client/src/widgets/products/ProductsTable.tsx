import styles from './ProductsTable.module.css';

interface ProductsTableProps {
    data: any;
}

export const ProductsTable: React.FC<ProductsTableProps> = ({ data }) => {
    return (
        <table className={styles.table}>
            <thead>
                <tr>
                    <th>Дата создания</th>
                    <th>Название</th>
                    <th>Цена</th>
                    <th>Рейтинг</th>
                    <th>ед. изм.</th>
                    <th>Номер</th>
                    <th>Владелец</th>
                    <th>Производитель</th>
                    <th>Цена производства</th>
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
                                    <button>edit</button>
                                </td>
                            )}
                        </tr>
                    );
                })}
            </tbody>
        </table>
    );
};
