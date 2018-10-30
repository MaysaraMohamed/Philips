package com.philips.backend.dao;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

public class InvoiceCategoryIdentity {

	  @NotNull
	    @Size(max = 50)
	    private String salesId;

	    @NotNull
	    private Integer categoryId;

	    public InvoiceCategoryIdentity() {

	    }

	    public InvoiceCategoryIdentity(String employeeId, Integer categoryId) {
	        this.salesId = employeeId;
	        this.categoryId = categoryId;
	    }


	    /**
		 * @return the invoiceId
		 */
		public String getInvoiceId() {
			return salesId;
		}

		/**
		 * @param invoiceId the invoiceId to set
		 */
		public void setInvoiceId(String invoiceId) {
			this.salesId = invoiceId;
		}

		/**
		 * @return the categoryId
		 */
		public Integer getCategoryId() {
			return categoryId;
		}

		/**
		 * @param categoryId the categoryId to set
		 */
		public void setCategoryId(Integer categoryId) {
			this.categoryId = categoryId;
		}

		@Override
	    public boolean equals(Object o) {
	        if (this == o) return true;
	        if (o == null || getClass() != o.getClass()) return false;

	        InvoiceCategoryIdentity that = (InvoiceCategoryIdentity) o;

	        if (!salesId.equals(that.salesId)) return false;
	        return categoryId == that.categoryId;
	    }

	    @Override
	    public int hashCode() {
	        int result = salesId.hashCode();
	        result = 31 * result + categoryId.hashCode();
	        return result;
	    }
}
