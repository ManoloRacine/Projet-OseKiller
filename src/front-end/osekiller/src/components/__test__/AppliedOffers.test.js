import { render, screen } from "@testing-library/react"
import { BrowserRouter } from "react-router-dom"
import AppliedOffers from "../../views/students/AppliedOffers"
import React from "react"

const MockAppliedOffers = () => {
    return (
        <BrowserRouter>
            <AppliedOffers/>
        </BrowserRouter>
    )
}

describe("applied offers test", () => {

    it("test one offer", async () => {

        const stubInitialState = [{position : "testPos", salary : 1, endDate : "10-12-2022", startDate : "10-11-2022", companyName: "testCompany"}]

        React.useState = jest.fn().mockReturnValueOnce([stubInitialState, {}]) ;

        const mockSetAuthenticatedUser = jest.fn().mockImplementation(authenticatedUser => {
            authenticatedUser = authenticatedUser;

            return authenticatedUser;
        })

        React.useContext = jest.fn().mockImplementation(() => ({
            authenticatedUser : {id : 1},
            setAuthenticatedUser : mockSetAuthenticatedUser,
        })) ;

        render(<AppliedOffers/>) ;
        
        const position = screen.getByText(/testPos/i);
        const salary = screen.getByText(/1\$/i);
        const endDate = screen.getByText(/10-12-2022/i);
        const startDate = screen.getByText(/10-11-2022/i);
        const companyName = screen.getByText(/testCompany/i);
        expect(position).toBeInTheDocument() ;
        expect(salary).toBeInTheDocument() ;
        expect(endDate).toBeInTheDocument() ;
        expect(startDate).toBeInTheDocument() ;
        expect(companyName).toBeInTheDocument() ;
    }) ;

    it("test give multiple offers", async () => {

        const stubInitialState = [{position : "testPos", salary : 1, endDate : "10-12-2022", startDate : "10-11-2022", companyName: "testCompany"},
         {position : "testPos", salary : 1, endDate : "10-12-2022", startDate : "10-11-2022", companyName: "testCompany"}, 
         {position : "testPos", salary : 1, endDate : "10-12-2022", startDate : "10-11-2022", companyName: "testCompany"}] ;

        React.useState = jest.fn().mockReturnValueOnce([stubInitialState, {}]) ;

        const mockSetAuthenticatedUser = jest.fn().mockImplementation(authenticatedUser => {
            authenticatedUser = authenticatedUser;

            return authenticatedUser;
        })

        React.useContext = jest.fn().mockImplementation(() => ({
            authenticatedUser : {id : 1},
            setAuthenticatedUser : mockSetAuthenticatedUser,
        })) ;

        render(<AppliedOffers/>) ;
        
        const positions = screen.queryAllByText(/testPos/i);
        const salaries = screen.queryAllByText(/1\$/i);
        const endDates = screen.queryAllByText(/10-12-2022/i);
        const startDates = screen.queryAllByText(/10-11-2022/i);
        const companyName = screen.queryAllByText(/testCompany/i) ;
        expect(positions.length).toBe(3) ;
        expect(salaries.length).toBe(3) ;
        expect(endDates.length).toBe(3) ;
        expect(startDates.length).toBe(3) ;
        expect(companyName.length).toBe(3) ;
    }) ;
})