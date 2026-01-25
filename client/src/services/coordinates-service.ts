import { ApiClient, ApiResponse } from './api-client';

export interface Coordinates {
    id: number;
    x: number;
    y: number;
}

export interface CoordinatesRequest {
    x: number;
    y: number;
}

export class CoordinatesService {
    static async getList(page: number, size: number): Promise<ApiResponse<Coordinates>> {
        return ApiClient.get<ApiResponse<Coordinates>>(`/api/coordinates/list?page=${page}&size=${size}`);
    }

    static async getAll(): Promise<Coordinates[]> {
        return ApiClient.get<Coordinates[]>('/api/coordinates/get-all');
    }

    static async create(coordinates: CoordinatesRequest): Promise<Coordinates> {
        return ApiClient.post<Coordinates>('/api/coordinates/create', coordinates);
    }

    static async update(id: number, coordinates: CoordinatesRequest): Promise<Coordinates> {
        return ApiClient.put<Coordinates>(`/api/coordinates/update/${id}`, coordinates);
    }

    static async delete(id: number): Promise<void> {
        return ApiClient.post(`/api/coordinates/delete/${id}`);
    }
}