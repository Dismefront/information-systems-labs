import { ApiClient, ApiResponse } from './api-client';

export interface Address {
    id: number;
    zipCode: string;
    town: {
        id: number;
        x: number;
        y: number;
        z: number;
    } | null;
}

export interface AddressRequest {
    zipCode: string;
    townId?: number;
}

export class AddressService {
    static async getList(page: number, size: number): Promise<ApiResponse<Address>> {
        return ApiClient.get<ApiResponse<Address>>(`/api/address/list?page=${page}&size=${size}`);
    }

    static async getAll(): Promise<Address[]> {
        return ApiClient.get<Address[]>('/api/address/get-all');
    }

    static async create(address: AddressRequest): Promise<Address> {
        return ApiClient.post<Address>('/api/address/create', address);
    }

    static async update(id: number, address: AddressRequest): Promise<Address> {
        return ApiClient.put<Address>(`/api/address/update/${id}`, address);
    }

    static async delete(id: number): Promise<void> {
        return ApiClient.post(`/api/address/delete/${id}`);
    }
}