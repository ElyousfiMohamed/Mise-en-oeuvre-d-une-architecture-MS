package ma.enset.billingservice.Service;

import ma.enset.billingservice.Dto.InvoiceMapper;
import ma.enset.billingservice.Dto.InvoiceRequestDto;
import ma.enset.billingservice.Dto.InvoiceResponseDto;
import ma.enset.billingservice.Entity.Invoice;
import ma.enset.billingservice.Repository.InvoiceRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import javax.transaction.Transactional;

import java.util.List;
import java.util.UUID;

@Service
@Transactional
public class InvoiceServiceImpl implements InvoiceService{
    private InvoiceRepository invoiceRepository;
    private InvoiceMapper invoiceMapper;

    public InvoiceServiceImpl(InvoiceRepository invoiceRepository, InvoiceMapper invoiceMapper) {
        this.invoiceRepository = invoiceRepository;
        this.invoiceMapper = invoiceMapper;
    }

    @Override
    public InvoiceResponseDto saveInvoice(InvoiceRequestDto invoiceRequestDto) {
        Invoice invoice = invoiceMapper.invoiceRequestDtoToInvoice(invoiceRequestDto);
        invoice.setId(UUID.randomUUID().toString());
        invoice.setDate(new java.util.Date());
        Invoice savedInvoice = invoiceRepository.save(invoice);
        return invoiceMapper.invoiceToInvoiceResponseDto(savedInvoice);
    }

    @Override
    public InvoiceResponseDto getInvoice(String id) {
        Invoice invoice = invoiceRepository.findById(id).get();
        return invoiceMapper.invoiceToInvoiceResponseDto(invoice);
    }

    @Override
    public InvoiceResponseDto updateInvoice(String id, InvoiceRequestDto invoiceRequestDto) {
        Invoice invoice = invoiceMapper.invoiceRequestDtoToInvoice(invoiceRequestDto);
        Invoice updatedInvoice = invoiceRepository.save(invoice);
        return invoiceMapper.invoiceToInvoiceResponseDto(updatedInvoice);
    }

    @Override
    public List<InvoiceResponseDto> getAllInvoices() {
        List<Invoice> invoices = invoiceRepository.findAll();
        return invoiceMapper.invoicesToInvoiceResponseDtos(invoices);
    }

    @Override
    public void deleteInvoice(String id) {
        invoiceRepository.deleteById(id);
    }

    @Override
    public List<InvoiceResponseDto> getInvoicesByCustomerId(String customerId) {
        List<Invoice> invoices = invoiceRepository.findAllByCustomerID(customerId);
        return invoiceMapper.invoicesToInvoiceResponseDtos(invoices);
    }
}
