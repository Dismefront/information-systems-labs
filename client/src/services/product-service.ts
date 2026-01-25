import { ApiClient, ApiResponse } from './api-client';

export interface Product {
    id: number;
    name: string;
    coordinates: {
        id: number;
        x: number;
        y: number;
    };
    creationDate: string;
    unitOfMeasure: string;
    manufacturer: {
        id: number;
        name: string;
        fullName: string;
    };
    price: number;
    manufactureCost: number;
    rating: number;
    partNumber: string;
    owner: {
        id: number;
        name: string;
    } | null;
    editable: boolean;
}

export interface ProductRequest {
    name: string;
    coordinatesId: number;
    unitOfMeasure: string;
    manufacturerId: number;
    price: number;
    manufactureCost: number;
    rating: number;
    partNumber: string;
    ownerId?: number;
}

export interface ManufacturerStats {
    manufacturerId: number;
    objectCount: number;
}

export class ProductService {
    static async getList(
        page: number,
        size: number,
        sortBy: string,
        sortDir: string
    ): Promise<ApiResponse<Product>> {
        return ApiClient.get<ApiResponse<Product>>(
            `/api/product/list?page=${page}&size=${size}&sortBy=${sortBy}&sortDir=${sortDir}`
        );
    }

    static async create(product: ProductRequest): Promise<Product> {
        return ApiClient.post<Product>('/api/product/create', product);
    }

    static async update(id: number, product: ProductRequest): Promise<Product> {
        return ApiClient.put<Product>(`/api/product/update/${id}`, product);
    }

    static async delete(id: number): Promise<void> {
        return ApiClient.post(`/api/product/delete/${id}`);
    }

    static async getByManufacturer(): Promise<ManufacturerStats[]> {
        return ApiClient.get<ManufacturerStats[]>('/api/product/by-manufacturer');
    }

    static async countByRating(rating: number): Promise<number> {
        return ApiClient.get<number>(`/api/product/by-rating?rating=${rating}`);
    }

    static async countByPartNumber(partNumber: string): Promise<number> {
        return ApiClient.get<number>(`/api/product/by-partNumber?partNumber=${partNumber}`);
    }

    static async getProductsByManufacturer(manufacturerId: number): Promise<Product[]> {
        return ApiClient.get<Product[]>(`/api/product/products-by-manufacturer?manufacturerId=${manufacturerId}`);
    }

    static async reducePrices(percent: number): Promise<void> {
        return ApiClient.get(`/api/product/reduce-price?percent=${percent}`);
    }
}