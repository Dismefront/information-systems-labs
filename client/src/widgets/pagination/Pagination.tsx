import { useNavigate } from 'react-router-dom';

interface PaginationProps {
    totalPages: number;
    currentPage: number;
    path: string;
}

export const Pagination: React.FC<PaginationProps> = ({ path, totalPages, currentPage }) => {
    const navigate = useNavigate();
    const updatePage = (pageNumber: number) => {
        navigate(`${path}/${pageNumber}`);
    };
    return (
        <div style={{ display: 'flex', gap: '2rem' }}>
            <div>
                page: {currentPage} / {totalPages}{' '}
            </div>
            <nav
                style={{ cursor: 'pointer', userSelect: 'none' }}
                onClick={() => {
                    if (currentPage === 1) {
                        return;
                    }
                    updatePage(currentPage - 1);
                }}
            >
                prev page
            </nav>
            <nav
                style={{ cursor: 'pointer', userSelect: 'none' }}
                onClick={() => {
                    if (currentPage === totalPages) {
                        return;
                    }
                    updatePage(currentPage + 1);
                }}
            >
                next page
            </nav>
        </div>
    );
};
